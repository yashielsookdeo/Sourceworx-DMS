using System.Collections.ObjectModel;
using PdfSharpCore;
using PdfSharpCore.Drawing;
using PdfSharpCore.Pdf;
using SkiaSharp;

namespace Sourceworx.DMS.NativeApp.Pages;

public partial class CameraCapturePage : ContentPage
{

    public ObservableCollection<ImageSource> CapturedImages { get; set; } = new();
    private List<FileResult> _imageFiles = new();

    public bool HasImages => _imageFiles.Any();

    public CameraCapturePage()
	{
		InitializeComponent();
        BindingContext = this;
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
        try
        {
            var pdf = new PdfDocument();

            foreach (var file in _imageFiles)
            {
                using var stream = await file.OpenReadAsync();

                using var memoryStream = new MemoryStream();
                await stream.CopyToAsync(memoryStream);
                memoryStream.Position = 0;

                using var image = ConvertToXImage(stream);

                var page = pdf.AddPage();
                page.Width = image.PixelWidth;
                page.Height = image.PixelHeight;

                using var gfx = XGraphics.FromPdfPage(page);
                gfx.DrawImage(image, 0, 0, page.Width, page.Height);
            }

            var filePath = System.IO.Path.Combine(FileSystem.CacheDirectory, $"Scanned_{DateTime.Now.Ticks}.pdf");

            using (var output = File.Create(filePath))
            {
                pdf.Save(output);
            }

            await Share.RequestAsync(new ShareFileRequest
            {
                Title = "Scanned PDF",
                File = new ShareFile(filePath)
            });
        }
        catch (Exception ex)
        {
            Console.WriteLine($"PDF generation failed: {ex.Message}");
            await Application.Current.MainPage.DisplayAlert("Error", "Failed to generate PDF.", "OK");
        }
    }

    private XImage ConvertToXImage(Stream inputStream)
    {
        using var skiaImage = SKBitmap.Decode(inputStream);
        using var encoded = skiaImage.Encode(SKEncodedImageFormat.Png, 100);
        var pngStream = new MemoryStream();
        encoded.SaveTo(pngStream);
        pngStream.Position = 0;

        return XImage.FromStream(() => pngStream);
    }


}