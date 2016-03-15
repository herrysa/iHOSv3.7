package com.huge.ihos.system.configuration.dictionary.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_dictionary_items", uniqueConstraints = { @UniqueConstraint( columnNames = { "dictionary_id", "value" } ) } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class DictionaryItem
    extends BaseObject
    implements Serializable {

    public String getValue() {
        return value;
    }

    @Column( name = "value", nullable = false, length = 50 )
    public void setValue( String value ) {
        this.value = value;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getDictionaryItemId() {
        return this.dictionaryItemId;
    }

    public void setDictionaryItemId( Long dictionaryItemId ) {
        this.dictionaryItemId = dictionaryItemId;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "dictionary_id", nullable = false )
    public Dictionary getDictionary() {
        return this.dictionary;
    }

    public void setDictionary( Dictionary TDictionary ) {
        this.dictionary = TDictionary;
    }

    @Column( name = "displayContent", nullable = false, length = 50 )
    public String getContent() {
        return this.content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    @Column( name = "orderNo" )
    public Long getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo( Long orderNo ) {
        this.orderNo = orderNo;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        DictionaryItem other = (DictionaryItem) obj;
        if ( content == null ) {
            if ( other.content != null )
                return false;
        }
        else if ( !content.equals( other.content ) )
            return false;
        if ( dictionary == null ) {
            if ( other.dictionary != null )
                return false;
        }
        else if ( !dictionary.equals( other.dictionary ) )
            return false;
        if ( dictionaryItemId == null ) {
            if ( other.dictionaryItemId != null )
                return false;
        }
        else if ( !dictionaryItemId.equals( other.dictionaryItemId ) )
            return false;
        if ( orderNo == null ) {
            if ( other.orderNo != null )
                return false;
        }
        else if ( !orderNo.equals( other.orderNo ) )
            return false;
        if ( value == null ) {
            if ( other.value != null )
                return false;
        }
        else if ( !value.equals( other.value ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( content == null ) ? 0 : content.hashCode() );
        result = prime * result + ( ( dictionary == null ) ? 0 : dictionary.hashCode() );
        result = prime * result + ( ( dictionaryItemId == null ) ? 0 : dictionaryItemId.hashCode() );
        result = prime * result + ( ( orderNo == null ) ? 0 : orderNo.hashCode() );
        result = prime * result + ( ( value == null ) ? 0 : value.hashCode() );
        return result;
    }

    @Override
    public String toString() {
        return "DictionaryItem [dictionaryItemId=" + dictionaryItemId + ", dictionary=" + dictionary + ", value=" + value + ", content=" + content
            + ", orderNo=" + orderNo + "]";
    }

    private Long dictionaryItemId;

    private Dictionary dictionary;

    private String value;

    private String content;

    private Long orderNo;

}
