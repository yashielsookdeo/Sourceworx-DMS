using Sourceworx.DMS.NativeApp.Services;

namespace Sourceworx.DMS.NativeApp
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();

            // Initialize the ServiceHelper with the current service provider
            ServiceHelper.Initialize(IPlatformApplication.Current.Services);
        }

        protected override Window CreateWindow(IActivationState? activationState)
        {
            return new Window(new AppShell());
        }
    }
}