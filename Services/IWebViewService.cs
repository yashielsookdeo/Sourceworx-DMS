namespace Sourceworx.DMS.NativeApp.Services
{
    public interface IWebViewService
    {
        Task ShowWebViewAsync(string htmlFileName);
        Task CloseWebViewAsync();
    }
}
