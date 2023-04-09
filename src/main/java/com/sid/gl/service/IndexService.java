package com.sid.gl.service;

import com.sid.gl.helper.Indices;
import com.sid.gl.helper.Util;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class IndexService {
    private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);
    private final List<String> INDICES_TO_CREATE=List.of(Indices.VEHICLE_INDEX);
    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public IndexService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @PostConstruct
    public void tryToCreateIndices() {
        recreateIndices(false);
    }


    public void recreateIndices(final boolean deleteExisting){
        final String settings = Util.loadAsString("static/es-settings.json");

        if (settings == null) {
            LOG.error("Failed to load index settings");
            return;
        }

         for(final String indexName:INDICES_TO_CREATE){
             try{
                 boolean indexExists=
                         restHighLevelClient
                                 .indices()
                                 .exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                 if(indexExists){
                     if (!deleteExisting) {
                         continue;
                     }

                     restHighLevelClient.indices().delete(
                             new DeleteIndexRequest(indexName),
                             RequestOptions.DEFAULT
                     );
                 }
                 final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                 createIndexRequest.settings(settings, XContentType.JSON);

                 final String mappings = loadMappings(indexName);
                 if (mappings != null) {
                     createIndexRequest.mapping(mappings, XContentType.JSON);
                 }

                 restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
             }catch (final Exception e){
                  LOG.error(e.getMessage());
             }
         }
    }

    private String loadMappings(String indexName) {
        final String mappings = Util.loadAsString("static/mappings/" + indexName + ".json");
        if (mappings == null) {
            LOG.error("Failed to load mappings for index with name '{}'", indexName);
            return null;
        }

        return mappings;
    }
}
