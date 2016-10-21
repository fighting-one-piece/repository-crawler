package org.platform.crawler.webmagic.modules.abstr.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PKAutoEntity<PK extends Serializable> extends PKEntity<PK> {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
    protected PK pk;

    @Override
    public PK getPK() {
        return pk;
    }

    @Override
    public void setPK(PK pk) {
        this.pk = pk;
    }

}
