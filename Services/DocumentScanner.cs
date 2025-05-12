using System;
using System.Threading.Tasks;

namespace Sourceworx.DMS.NativeApp.Services
{
    public class DocumentScanner
    {
        public async Task<string> ScanDocumentAsync()
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

                // Launch the CAJV app using app linking
                var supportsUri = await Launcher.CanOpenAsync("cajv://scan");
                
                if (supportsUri)
                {
                    await Launcher.OpenAsync("cajv://scan");
                    
                    // This is a placeholder for the actual implementation
                    // In a real implementation, you would need to set up app linking between the two apps
                    // and handle the return of the scanned document
                    
                    return "document_scanned.pdf";
                }
                else
                {
                    // If CAJV app is not installed, show an alert
                    await Shell.Current.DisplayAlert("App Not Found", "The document scanning app is not installed.", "OK");
                    return string.Empty;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error scanning document: {ex.Message}");
                await Shell.Current.DisplayAlert("Error", "An error occurred while scanning the document.", "OK");
                return string.Empty;
            }
        }

        // Method to save a document to the app's storage
        public async Task<string> SaveDocumentAsync(byte[] documentData, string taskId)
        {
            try
            {
                string folderPath = FileSystem.AppDataDirectory;
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
    }
}
