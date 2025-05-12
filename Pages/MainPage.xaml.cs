using Sourceworx.DMS.NativeApp.Models;
using Sourceworx.DMS.NativeApp.PageModels;

namespace Sourceworx.DMS.NativeApp.Pages
{
    public partial class MainPage : ContentPage
    {
        public MainPage(MainPageModel model)
        {
            InitializeComponent();
            BindingContext = model;
        }
    }
}