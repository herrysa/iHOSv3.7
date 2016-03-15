package com.huge.ihos.system.reportManager.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "Test" )
public class Test {
    private Long id;

    private String title;

    private String value;

    private String value2;

    private String value3;

    private String value4;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2( String value2 ) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3( String value3 ) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4( String value4 ) {
        this.value4 = value4;
    }

}
