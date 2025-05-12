using CommunityToolkit.Mvvm.Input;
using Sourceworx.DMS.NativeApp.Models;

namespace Sourceworx.DMS.NativeApp.PageModels
{
    public interface IProjectTaskPageModel
    {
        IAsyncRelayCommand<ProjectTask> NavigateToTaskCommand { get; }
        bool IsBusy { get; }
    }
}