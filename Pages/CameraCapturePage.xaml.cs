using System.Collections.ObjectModel;
using PdfSharpCore;
using PdfSharpCore.Drawing;
using PdfSharpCore.Pdf;
using SkiaSharp;
using Sourceworx.DMS.NativeApp.Services;

namespace Sourceworx.DMS.NativeApp.Pages;

public partial class CameraCapturePage : ContentPage
{
    public ObservableCollection<ImageSource> CapturedImages { get; set; } = new();
    private readonly List<FileResult> _imageFiles = new();
    private readonly int _taskId;

    public bool HasImages => _imageFiles.Count > 0;

    public CameraCapturePage(int taskId = 0)
    {
        InitializeComponent();
        BindingContext = this;
        _taskId = taskId;
    }

    private async void OnCaptureImageClicked(object sender, EventArgs e)
    {
        try
        {
            var photo = await MediaPicker.CapturePhotoAsync();
            if (photo != null)
            {
                _imageFiles.Add(photo);
                var stream = await photo.OpenReadAsync();
                CapturedImages.Add(ImageSource.FromStream(() => stream));

                OnPropertyChanged(nameof(HasImages));
            }
        }
        catch (Exception ex)
        {
            await DisplayAlert("Error", ex.Message, "OK");
        }
    }

    private async void OnGeneratePdfClicked(object sender, EventArgs e)
    {
        if (_imageFiles.Count == 0)
        {
            await DisplayAlert("Error", "No images captured. Please capture at least one image.", "OK");
            return;
        }

        try
        {
            string filePath;

            if (_taskId > 0)
            {
                // Use the DocumentScanner service to generate and save the PDF
                filePath = await DocumentScanner.GeneratePdfFromImagesAsync(_imageFiles, _taskId);

                if (string.IsNullOrEmpty(filePath))
                {
                    await DisplayAlert("Error", "Failed to generate PDF.", "OK");
                    return;
                }

                await DisplayAlert("Success", "PDF generated and saved successfully.", "OK");
            }
            else
            {
                // Use the original implementation if no task ID is provided or DocumentScanner is not available
                var pdf = new PdfDocument();

                foreach (var file in _imageFiles)
                {
                    using var stream = await file.OpenReadAsync();
                    using var image = ConvertToXImage(stream);

                    var page = pdf.AddPage();
                    page.Width = image.PixelWidth;
                    page.Height = image.PixelHeight;

                    using var gfx = XGraphics.FromPdfPage(page);
                    gfx.DrawImage(image, 0, 0, page.Width, page.Height);
                }

                filePath = Path.Combine(FileSystem.CacheDirectory, $"Scanned_{DateTime.Now.Ticks}.pdf");

                using var output = File.Create(filePath);
                pdf.Save(output);
            }

            // Ask the user if they want to share the PDF
            bool share = await DisplayAlert("Share PDF", "Do you want to share the generated PDF?", "Yes", "No");

            if (share)
            {
                await Share.RequestAsync(new ShareFileRequest
                {
                    Title = "Scanned PDF",
                    File = new ShareFile(filePath)
                });
            }

            // Close the camera page and return to the previous page
            await Navigation.PopModalAsync();
        }
        catch (Exception ex)
        {
            Console.WriteLine($"PDF generation failed: {ex.Message}");
            await DisplayAlert("Error", "Failed to generate PDF.", "OK");
        }
    }

    private async void OnCancelClicked(object sender, EventArgs e)
    {
        if (_imageFiles.Count > 0)
        {
            bool confirm = await DisplayAlert("Confirm Cancel",
                "Are you sure you want to cancel? All captured images will be lost.",
                "Yes", "No");

            if (!confirm)
            {
                return;
            }
        }

        await Navigation.PopModalAsync();
    }

    private static XImage ConvertToXImage(Stream inputStream)
    {
        using var skiaImage = SKBitmap.Decode(inputStream);
        using var encoded = skiaImage.Encode(SKEncodedImageFormat.Png, 100);
        var pngStream = new MemoryStream();
        encoded.SaveTo(pngStream);
        pngStream.Position = 0;

        return XImage.FromStream(() => pngStream);
    }
}