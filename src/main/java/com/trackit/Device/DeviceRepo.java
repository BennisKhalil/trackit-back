package com.trackit.Device;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface DeviceRepo extends ElasticsearchRepository<Device,String> {
    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"device_id\" : \"?0\"}}}}")
    Device findByDeviceId(String id);

}
