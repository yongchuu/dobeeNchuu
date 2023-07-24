package com.dnc.server.es.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "dnc-es-order")
public class ESOrderDto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uuid;

    @Field(type = FieldType.Text, name="publisher")
    private String publisher;

    @Field(type = FieldType.Text, name="author")
    private String author;

    @Field(type = FieldType.Integer, name="price")
    private Integer price;

    @Field(type = FieldType.Text, name="bookTitle")
    private String bookTitle;

    @Field(type = FieldType.Text, name="cnt")
    private String cnt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    @Field(type = FieldType.Date, name="date")
    private Date date;

    @Override
    public String toString(){
        return "[uuid] : " + uuid
                + ", [bookTitle] : " + bookTitle
                + ", [author] : " + author
                + ", [publisher] : " + publisher
                + ", [price] : " + price
                + ", [cnt] : " + cnt
                + ", [date] : " + date;
    }
}
