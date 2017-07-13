package byaj.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cloudinary.StoredFile;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

/**
 * Created by student on 6/27/17.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    //@NotNull
    //@Min(1)
    private int postID;

    @ManyToMany(mappedBy = "likes")
    private Collection<User> users;

    private String postName;
    
    @Basic
    private String image;

    private String postAuthor;

    @Column(columnDefinition="integer default -1")
    private int postUser;
    
    

    @Lob
    @Type(type = "text")
    //@Column(columnDefinition="blob default http://res.cloudinary.com/andrewjonesdev/image/upload/c_fill,h_100,w_100/v1499897311/Empty_xay49d.png")
    private String picUrl;

    private Date postDate=new Date();



    public int getPostID() {
        return postID;
    }

    /*public void setMateID(int postID) {
        this.postID = postID;
    }*/
    public String getPostName() {
        return postName;
    }

    public void setPostName (String postName) {
        this.postName = postName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl (String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor (String postAuthor) {
        this.postAuthor = postAuthor;
    }
    
    public int getPostUser() {
        return postUser;
    }

    public void setPostUser (int postUser) {
        this.postUser = postUser;
    }

    public Date getPostDate() {
        return postDate;
    }

    public String getFormatDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE MMMMM dd, yyyy hh:mm a zzzz", Locale.US);
        return format.format(postDate);
    }

<<<<<<< HEAD
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	 public StoredFile getUpload() {
	        StoredFile file = new StoredFile();
	        file.setPreloadedFile(image);
	        return file;
	    }

	    public void setUpload(StoredFile file) {
	        this.image = file.getPreloadedFile();
	    }
=======
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
>>>>>>> 612928ed85b517cbb38523f4a8dae806a36179ed
}
