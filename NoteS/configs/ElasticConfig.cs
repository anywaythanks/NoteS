using Elastic.Clients.Elasticsearch;

namespace NoteS.configs;

public class ElasticConfig
{
    public static void Configuration(IHostApplicationBuilder builder)
    {
        var services = builder.Services;
        var configuration = builder.Configuration;
        services.AddSingleton<ElasticsearchClient>(_ =>
        {
            var settings = new ElasticsearchClientSettings(new Uri($"{configuration["Elastic:server-url"]}"))
                .DefaultIndex("notes");

            return new ElasticsearchClient(settings);
        });
    }
}