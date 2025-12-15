package kirisame.magic.user_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username; // Este será el "Nickname"
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @Column(name = "role") // Esto creará la columna en la BD
    private String role;

    // --- NUEVOS CAMPOS ---
    private String firstName; // Nombre
    private String lastName;  // Apellido
    private String phone;     // Telefono
    private String dateOfBirth; // Fecha nacimiento

    @Lob // Permite guardar textos muy largos (Base64 de la imagen)
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;    // Foto de perfil

    // Constructors
    public User() {}
    
    // Constructor completo (puedes generar uno nuevo o usar setters)
    public User(String username, String email, String password, String firstName, String lastName, String phone, String dateOfBirth, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
    }
    
    // Getters and Setters (¡Genera los getters y setters para los nuevos campos!)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}