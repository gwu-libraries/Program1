/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopapplication2;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
/**
 *
 * @author gilani
 */
@Entity
@Table(name = "Project")
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findByProjectid", query = "SELECT p FROM Project p WHERE p.projectid = :projectid"),
    @NamedQuery(name = "Project.findByProjectname", query = "SELECT p FROM Project p WHERE p.projectname = :projectname"),
    @NamedQuery(name = "Project.findByProjectdate", query = "SELECT p FROM Project p WHERE p.projectdate = :projectdate"),
    @NamedQuery(name = "Project.findByProjectPath", query = "SELECT p FROM Project p WHERE p.projectPath = :projectPath")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Project_id",unique = true, nullable = false)
    private Integer projectid;
    @Column(name = "Project_name")
    private String projectname;
    @Basic(optional = false)
    @Column(name = "Project_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectdate;
    @Column(name = "Project_Path")
    private String projectPath;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectid",targetEntity=Book.class,fetch=FetchType.EAGER)
    private Collection<Book> bookCollection;

    public Project() {
    }

    public Project(Integer projectid) {
        this.projectid = projectid;
    }

    public Project(Integer projectid, Date projectdate) {
        this.projectid = projectid;
        this.projectdate = projectdate;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public Date getProjectdate() {
        return projectdate;
    }

    public void setProjectdate(Date projectdate) {
        this.projectdate = projectdate;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public Collection<Book> getBookCollection() {
        return bookCollection;
    }

    public void setBookCollection(Collection<Book> bookCollection) {
        this.bookCollection = bookCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication2.Project[projectid=" + projectid + "]";
    }

}
