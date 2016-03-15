package com.huge.ihos.system.reportManager.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "Test_Date" )
public class TestDate {
    private Long id;

    private String dataSource;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource( String dataSource ) {
        this.dataSource = dataSource;
    }

}
