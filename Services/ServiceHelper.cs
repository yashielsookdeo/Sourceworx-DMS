namespace Sourceworx.DMS.NativeApp.Services
{
    public static class ServiceHelper
    {
        public static IServiceProvider Services { get; private set; }

        public static void Initialize(IServiceProvider serviceProvider) =>
            Services = serviceProvider;

        public static T GetService<T>() where T : class =>
            Services.GetService<T>() ?? throw new InvalidOperationException($"Service of type {typeof(T).Name} not found");
    }
}
