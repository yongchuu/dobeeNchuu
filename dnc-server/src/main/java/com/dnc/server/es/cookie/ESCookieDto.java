package com.dnc.server.es.cookie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "dnc-es-cookie")
public class ESCookieDto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uuid;

    @Field(type = FieldType.Text, name="clientId")
    private Integer clientId;

    @Field(type = FieldType.Text, name="cookie")
    private String cookie;

    @Field(type = FieldType.Text, name="userAgent")
    private String userAgent;

    @Override
    public String toString(){
        return "[clientId] : " + clientId + ", [cookie] : " + cookie + ", [userAgent] : " + userAgent;
    }
}
