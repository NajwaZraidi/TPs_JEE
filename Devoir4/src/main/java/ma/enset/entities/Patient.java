package ma.enset.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
// Data => setters et getters  NoArgsConstructor => constructeur sans parametre
@Entity //Notion de JPA
@Data @AllArgsConstructor @NoArgsConstructor
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Temporal(TemporalType.DATE) //garder la forme de date
    private Date dateNaissance;
    private boolean malade;
    private int score;
}
