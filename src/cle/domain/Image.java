package cle.domain;

// Generated 11-Mar-2011 20:55:57 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Image generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "image")
public class Image implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer imageid;
	private Resource resource;
	private String imagename;
	private byte[] imagedata;

	public Image() {
	}

	public Image(Resource resource) {
		this.resource = resource;
	}

	public Image(Resource resource, String imagename, byte[] imagedata) {
		this.resource = resource;
		this.imagename = imagename;
		this.imagedata = imagedata;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "imageid", unique = true, nullable = false)
	public Integer getImageid() {
		return this.imageid;
	}

	public void setImageid(Integer imageid) {
		this.imageid = imageid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resourceid", nullable = false)
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Column(name = "imagename", length = 45)
	public String getImagename() {
		return this.imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	@Column(name = "imagedata")
	public byte[] getImagedata() {
		return this.imagedata;
	}

	public void setImagedata(byte[] imagedata) {
		this.imagedata = imagedata;
	}

}
