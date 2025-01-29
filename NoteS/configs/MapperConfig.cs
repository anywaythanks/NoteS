using LR.mappers;

namespace NoteS.configs;

public class MapperConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        builder.Services.AddTransient<AccountMapper>();
        builder.Services.AddTransient<ProductMapper>();
        builder.Services.AddTransient<PurchasesMapper>();
    }
}