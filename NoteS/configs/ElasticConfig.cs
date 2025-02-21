using Elastic.Clients.Elasticsearch;

namespace NoteS.configs;

public class ElasticConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        var services = builder.Services;
        var configuration = builder.Configuration;
        services.AddSingleton<ElasticData>(_ =>
            new ElasticData($"{configuration["Elastic:index-name"]}", $"{configuration["Elastic:model-id"]}"));
        services.AddSingleton<ElasticsearchClient>(_ =>
        {
            var settings = new ElasticsearchClientSettings(new Uri($"{configuration["Elastic:server-url"]}"))
                .DefaultIndex($"{configuration["Elastic:index-name"]}")
                // .DefaultFieldNameInferrer(s => s)
                .EnableDebugMode();
            return new ElasticsearchClient(settings);
        });
    }
}