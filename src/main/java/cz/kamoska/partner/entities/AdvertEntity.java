package cz.kamoska.partner.entities;

import cz.kamoska.partner.enums.AdvertState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 3.10.12
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "advert")
public class AdvertEntity {

	@Id
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Size(max = 50)
	@Column(name = "title")
	@NotNull
	private String title;

	@Size(max = 90)
	@Column(name = "body")
	@NotNull
	private String body;

	@NotNull
	@Column(name = "url")
	@Size(max = 1024)
	private String url;

	@NotNull
	@Column(name = "picture")
	private String pictureFileName; // nazev souboru, ktery se pouzije jako obrazek. Obrazek musi byt ulozeny nekde v systemu a tedy musi existovat provider (Servlet) na ziskavani obrazku

	@NotNull
	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private AdvertState state;

	@OneToMany(fetch = FetchType.EAGER)
	private List<SectionEntity> sectionEntityList;

}
