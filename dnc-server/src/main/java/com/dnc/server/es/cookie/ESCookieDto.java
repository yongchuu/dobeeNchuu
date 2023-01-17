package com.dnc.server.es.cookie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

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

    @Field(type = FieldType.Text, name="version")
    private String version;

    @Field(type =FieldType.Date, name="timestamp")
    private Date timestamp;

    @Override
    public String toString(){
        return "[clientId] : " + clientId
                + ", [cookie] : " + cookie
                + ", [userAgent] : " + userAgent
                + ", [version] : " + version
                + ", [timestamp] : " + timestamp;
    }
}
