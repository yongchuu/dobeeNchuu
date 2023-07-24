package com.dnc.server.es.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Document(indexName = "dnc-es-logging")
public class ESLoggingDto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uuid;

    @Field(type =FieldType.Date, name="timestamp")
    private Date timestamp;

    @Field(type = FieldType.Text, name="site")
    private String site;

    @Field(type = FieldType.Text, name="ticketNm")
    private String ticketNm;

    @Field(type = FieldType.Integer, name="rank")
    private Integer rank;

    @Field(type = FieldType.Text, name="deviceId")
    private String deviceId;

    @Field(type = FieldType.Text, name="status")
    private String status;

    @Field(type = FieldType.Double, name="loadTime")
    private Double loadTime;

    @Field(type = FieldType.Text, name="ip")
    private String ip;

    @Field(type = FieldType.Text, name="id")
    private String id;

    @Field(type = FieldType.Text, name="pw")
    private String pw;

    @Field(type = FieldType.Text, name="function")
    private String function;

    @Field(type = FieldType.Text, name="cookie")
    private String cookie;

    @Field(type = FieldType.Text, name="errorMsg")
    private String errorMsg;

    @Override
    public String toString(){
        return "[uuid] : " + uuid
                + ", [timestamp] : " + timestamp
                + ", [site] : " + site
                + ", [ticketNm] : " + ticketNm
                + ", [rank] : " + rank
                + ", [deviceId] : " + deviceId
                + ", [status] : " + status
                + ", [loadTime] : " + loadTime
                + ", [ip] : " + ip
                + ", [id] : " + id
                + ", [pw] : " + pw
                + ", [function] : " + function
                + ", [cookie] : " + cookie
                + ", [errorMsg] : " + errorMsg;
    }
}
