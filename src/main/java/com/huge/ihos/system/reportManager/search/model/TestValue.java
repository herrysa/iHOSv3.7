package com.huge.ihos.system.reportManager.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "TestValue" )
public class TestValue {
    private Long id;

    private String valueName;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName( String valueName ) {
        this.valueName = valueName;
    }

}
