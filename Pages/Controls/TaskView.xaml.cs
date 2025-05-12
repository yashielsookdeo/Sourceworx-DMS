using System.Windows.Input;
using Sourceworx.DMS.NativeApp.Models;
using Sourceworx.DMS.NativeApp.Services;

namespace Sourceworx.DMS.NativeApp.Pages.Controls
{
    public partial class TaskView
    {
        public TaskView()
        {
            InitializeComponent();
        }

        public static readonly BindableProperty TaskCompletedCommandProperty = BindableProperty.Create(
            nameof(TaskCompletedCommand),
            typeof(ICommand),
            typeof(TaskView),
            null);

        public ICommand TaskCompletedCommand
        {
            get => (ICommand)GetValue(TaskCompletedCommandProperty);
            set => SetValue(TaskCompletedCommandProperty, value);
        }

        private async void CheckBox_CheckedChanged(object sender, CheckedChangedEventArgs e)
        {
            var checkbox = (CheckBox)sender;

            if (checkbox.BindingContext is not ProjectTask task)
                return;

            if (task.IsCompleted == e.Value)
                return;

            task.IsCompleted = e.Value;

            // If the task is being marked as completed, launch the document scanner
            if (task.IsCompleted)
            {
                bool shouldScan = await Shell.Current.DisplayAlert(
                    "Scan Document",
                    "Would you like to scan a document for this task?",
                    "Yes", "No");

                if (shouldScan)
                {
                    try
                    {
                        // Launch the document scanner using the static method
                        string documentPath = await DocumentScanner.ScanDocumentAsync();

                        if (!string.IsNullOrEmpty(documentPath))
                        {
                            // Update the task with the document path
                            task.DocumentPath = documentPath;
                            await Shell.Current.DisplayAlert("Success", "Document scanned and saved successfully.", "OK");
                        }
                    }
                    catch (Exception ex)
                    {
                        await Shell.Current.DisplayAlert("Error", $"Failed to scan document: {ex.Message}", "OK");
                    }
                }
            }

            TaskCompletedCommand?.Execute(task);
        }
    }
}