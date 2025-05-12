using System;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using Sourceworx.DMS.NativeApp.Pages;
using PdfSharpCore;
using PdfSharpCore.Drawing;
using PdfSharpCore.Pdf;
using SkiaSharp;

namespace Sourceworx.DMS.NativeApp.Services
{
    public class DocumentScanner
    {
        public static async Task<string> ScanDocumentAsync()
        {
            try
            {
                // Check if camera permission is granted
                var status = await Permissions.CheckStatusAsync<Permissions.Camera>();
                if (status != PermissionStatus.Granted)
                {
                    status = await Permissions.RequestAsync<Permissions.Camera>();
                    if (status != PermissionStatus.Granted)
                    {
                        await Shell.Current.DisplayAlert("Permission Denied", "Camera permission is required to scan documents.", "OK");
                        return string.Empty;
                    }
                }

                // Navigate to the CameraCapturePage
                var cameraPage = new CameraCapturePage();
                await Shell.Current.Navigation.PushModalAsync(cameraPage);

                // The result will be handled when the user completes the scanning process
                // and returns to the previous page

                // For now, we'll return an empty string as the actual PDF generation
                // is handled in the CameraCapturePage
                return string.Empty;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error scanning document: {ex.Message}");
                await Shell.Current.DisplayAlert("Error", "An error occurred while scanning the document.", "OK");
                return string.Empty;
            }
        }

        // Method to save a document to the app's storage
        public static async Task<string> SaveDocumentAsync(byte[] documentData, int taskId)
        {
            try
            {
                string folderPath = Path.Combine(FileSystem.AppDataDirectory, "Documents");

                // Create the directory if it doesn't exist
                if (!Directory.Exists(folderPath))
                {
                    Directory.CreateDirectory(folderPath);
                }

                string fileName = $"task_{taskId}_{DateTime.Now:yyyyMMdd_HHmmss}.pdf";
                string filePath = Path.Combine(folderPath, fileName);

                await File.WriteAllBytesAsync(filePath, documentData);
                return filePath;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error saving document: {ex.Message}");
                return string.Empty;
            }
        }

        // Method to generate a PDF from a list of image files
        public static async Task<string> GeneratePdfFromImagesAsync(List<FileResult> imageFiles, int taskId)
        {
            try
            {
                var pdf = new PdfDocument();

                foreach (var file in imageFiles)
                {
                    using var stream = await file.OpenReadAsync();
                    using var image = ConvertToXImage(stream);

                    var page = pdf.AddPage();
                    page.Width = image.PixelWidth;
                    page.Height = image.PixelHeight;

                    using var gfx = XGraphics.FromPdfPage(page);
                    gfx.DrawImage(image, 0, 0, page.Width, page.Height);
                }

                string folderPath = Path.Combine(FileSystem.AppDataDirectory, "Documents");

                // Create the directory if it doesn't exist
                if (!Directory.Exists(folderPath))
                {
                    Directory.CreateDirectory(folderPath);
                }

                string fileName = $"task_{taskId}_{DateTime.Now:yyyyMMdd_HHmmss}.pdf";
                string filePath = Path.Combine(folderPath, fileName);

                using var output = File.Create(filePath);
                pdf.Save(output);

                return filePath;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"PDF generation failed: {ex.Message}");
                return string.Empty;
            }
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

        // Method to get all documents for a task
        public static List<string> GetDocumentsForTask(int taskId)
        {
            try
            {
                string folderPath = Path.Combine(FileSystem.AppDataDirectory, "Documents");

                if (!Directory.Exists(folderPath))
                {
                    return [];
                }

                var files = Directory.GetFiles(folderPath, $"task_{taskId}_*.pdf");
                return [.. files];
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error getting documents: {ex.Message}");
                return [];
            }
        }
    }
}
