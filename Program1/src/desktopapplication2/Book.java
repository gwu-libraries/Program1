/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopapplication2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author gilani
 */
@Entity
@Table(name = "book")
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByBarcode", query = "SELECT b FROM Book b WHERE b.barcode = :barcode"),
    @NamedQuery(name = "Book.findByPath", query = "SELECT b FROM Book b WHERE b.path = :path"),
    @NamedQuery(name = "Book.findByDatecreated", query = "SELECT b FROM Book b WHERE b.datecreated = :datecreated"),
    @NamedQuery(name = "Book.findByTiffstart", query = "SELECT b FROM Book b WHERE b.tiffstart = :tiffstart"),
    @NamedQuery(name = "Book.findByTiffend", query = "SELECT b FROM Book b WHERE b.tiffend = :tiffend"),
    @NamedQuery(name = "Book.findByLanguage", query = "SELECT b FROM Book b WHERE b.language = :language"),
    @NamedQuery(name = "Book.findByOCRed", query = "SELECT b FROM Book b WHERE b.oCRed = :oCRed"),
    @NamedQuery(name = "Book.findByJpc", query = "SELECT b FROM Book b WHERE b.jpc = :jpc"),
    @NamedQuery(name = "Book.findMatch", query = "SELECT b FROM Book b Inner Join b.projectid a WHERE b.projectid = :projId"),
    @NamedQuery(name = "Book.deleteBook", query = "DELETE from Book b WHERE b.barcode = :barcode"),
    @NamedQuery(name = "Book.findByJpeg", query = "SELECT b FROM Book b WHERE b.jpeg = :jpeg")})
public class Book implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Barcode")
    private String barcode;
    @Basic(optional = false)
    @Column(name = "Path")
    private String path;
    @Basic(optional = false)
    @Column(name = "Date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreated;
    @Column(name = "Tiff_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiffstart;
    @Column(name = "Tiff_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiffend;
    @Column(name = "Language")
    private String language;
    @Column(name = "OCRed")
    private Boolean oCRed;
    @Column(name = "JPC")
    private Boolean jpc;
    @Column(name = "JPEG")
    private Boolean jpeg;
    @JoinColumn(name = "Project_id", referencedColumnName = "Project_id")
    @ManyToOne(optional = false)
    private Project projectid;

    public Book() {
    }

    public Book(String barcode) {
        this.barcode = barcode;
    }

    public Book(String barcode, String path, Date datecreated) {
        this.barcode = barcode;
        this.path = path;
        this.datecreated = datecreated;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        String oldBarcode = this.barcode;
        this.barcode = barcode;
        changeSupport.firePropertyChange("barcode", oldBarcode, barcode);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        String oldPath = this.path;
        this.path = path;
        changeSupport.firePropertyChange("path", oldPath, path);
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        Date oldDatecreated = this.datecreated;
        this.datecreated = datecreated;
        changeSupport.firePropertyChange("datecreated", oldDatecreated, datecreated);
    }

    public Date getTiffstart() {
        return tiffstart;
    }

    public void setTiffstart(Date tiffstart) {
        Date oldTiffstart = this.tiffstart;
        this.tiffstart = tiffstart;
        changeSupport.firePropertyChange("tiffstart", oldTiffstart, tiffstart);
    }

    public Date getTiffend() {
        return tiffend;
    }

    public void setTiffend(Date tiffend) {
        Date oldTiffend = this.tiffend;
        this.tiffend = tiffend;
        changeSupport.firePropertyChange("tiffend", oldTiffend, tiffend);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        String oldLanguage = this.language;
        this.language = language;
        changeSupport.firePropertyChange("language", oldLanguage, language);
    }

    public Boolean getOCRed() {
        return oCRed;
    }

    public void setOCRed(Boolean oCRed) {
        Boolean oldOCRed = this.oCRed;
        this.oCRed = oCRed;
        changeSupport.firePropertyChange("OCRed", oldOCRed, oCRed);
    }

    public Boolean getJpc() {
        return jpc;
    }

    public void setJpc(Boolean jpc) {
        Boolean oldJpc = this.jpc;
        this.jpc = jpc;
        changeSupport.firePropertyChange("jpc", oldJpc, jpc);
    }

    public Boolean getJpeg() {
        return jpeg;
    }

    public void setJpeg(Boolean jpeg) {
        Boolean oldJpeg = this.jpeg;
        this.jpeg = jpeg;
        changeSupport.firePropertyChange("jpeg", oldJpeg, jpeg);
    }

    public Project getProjectid() {
        return projectid;
    }

    public void setProjectid(Project projectid) {
        Project oldProjectid = this.projectid;
        this.projectid = projectid;
        changeSupport.firePropertyChange("projectid", oldProjectid, projectid);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (barcode != null ? barcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.barcode == null && other.barcode != null) || (this.barcode != null && !this.barcode.equals(other.barcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication2.Book[barcode=" + barcode + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
