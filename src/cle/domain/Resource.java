package cle.domain;

// Generated 11-Mar-2011 20:55:57 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Resource generated by hbm2java
 */
@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "resource")
@JsonAutoDetect(setterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE)
public class Resource implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Integer resourceid;
	@JsonProperty
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String title;
	private String fileLocation;
	@JsonProperty
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String tags;
	@JsonProperty
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String description;
	@JsonProperty
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String content;
	@JsonProperty
	private Date modified;
	@JsonProperty
	private Date created;
	@JsonProperty
	private Integer visibility;
	private User user;
	CommonsMultipartFile fileData;
	private Set<Collection> collections = new HashSet<Collection>(0);
	private Set<Comment> comments = new HashSet<Comment>(0);
	private Set<Image> images = new HashSet<Image>(0);
	@IndexedEmbedded
	@OneToMany
	private Set<Page> pages = new HashSet<Page>(0);

	public Resource() {
	}

	public Resource(String title, String tags, String description,
			Date modified, User user) {
		this.title = title;
		this.tags = tags;
		this.description = description;
		this.modified = modified;
		this.user = user;
	}

	public Resource(String title, String fileLocation, String tags,
			String description, String content, Date modified, User user,
			Date created, Integer visibility, Set<Collection> collections,
			Set<Comment> comments, Set<Image> images, Set<Page> pages) {
		this.title = title;
		this.fileLocation = fileLocation;
		this.tags = tags;
		this.description = description;
		this.content = content;
		this.modified = modified;
		this.created = created;
		this.visibility = visibility;
		this.collections = collections;
		this.comments = comments;
		this.images = images;
		this.pages = pages;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "resourceid", unique = true, nullable = false)
	public Integer getResourceid() {
		return this.resourceid;
	}

	public void setResourceid(Integer resourceid) {
		this.resourceid = resourceid;
	}

	@Column(name = "title", nullable = false, length = 45)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "fileLocation", length = 45)
	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Column(name = "tags", nullable = false, length = 65535)
	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Column(name = "description", nullable = false, length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", nullable = false, length = 19)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "visibility")
	public Integer getVisibility() {
		return this.visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "collection_has_resource", joinColumns = { @JoinColumn(name = "resource_resourceid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "collection_collectionid", nullable = false, updatable = false) })
	public Set<Collection> getCollections() {
		return this.collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<Image> getImages() {
		return this.images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<Page> getPages() {
		return this.pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	@Transient
	public CommonsMultipartFile getFileData() {

		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public void addCollection(Collection c) {
		c.getResources().add(this);
		getCollections().add(c);

	}

	public void removeCollection(Collection c) {
		c.getResources().remove(this);
		getCollections().remove(c);

	}
	
	@Override
	public boolean equals(Object compareObj)// Roles are equal when the role
											// names are the same, - don't care
											// if objects are equal
	{
		if (this == compareObj) // Are they exactly the same instance?
			return true;

		if (compareObj == null) // Is the object being compared null?
			return false;

		if (!(compareObj instanceof Resource)) // Is the object being compared
													// also
			// a Person?
			return false;

		Resource compare= (Resource) compareObj; // Convert the object
															// to a
		// Person

		return this.resourceid.equals(compare.resourceid); // Are they
																	// equal?
	}
	 public int hashCode() {
	        return this.resourceid;
	    }

}
