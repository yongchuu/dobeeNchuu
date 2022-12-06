package com.dnc.server.es.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "dnc-es-member")
public class ESMemberDto {

    @Id
    private String email;

    @Field(type = FieldType.Text, name="tel")
    private String tel;

    @Field(type = FieldType.Text, name="name")
    private String name;

    @Field(type = FieldType.Text, name="sex")
    private String sex;

    @Field(type = FieldType.Text, name="password")
    private String password;

    @Field(type = FieldType.Text, name="type")
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    @Field(type = FieldType.Date, name="birth")
    private Date birth;

    @Override
    public String toString(){
        return "[email] : " + email
                + ", [name] : " + name
                + ", [sex] : " + sex
                + ", [type] : " + type
                + ", [birth] : " + birth;
    }
}
