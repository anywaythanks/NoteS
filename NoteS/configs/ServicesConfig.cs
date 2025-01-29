using LR.services;

namespace NoteS.configs;

public class ServicesConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddTransient<AccountInformationService>();
        builder.Services.AddTransient<AccountRegisterService>();
        builder.Services.AddTransient<AccountReplenishService>();
        builder.Services.AddTransient<ProductDeleteService>();
        builder.Services.AddTransient<ProductInformationService>();
        builder.Services.AddTransient<ProductRegisterService>();
        builder.Services.AddTransient<PurchasesInformationService>();
        builder.Services.AddTransient<ProductSellerService>();
        builder.Services.AddTransient<PurchasesRegisterService>();
        builder.Services.AddTransient<LoginService>();
    }
}