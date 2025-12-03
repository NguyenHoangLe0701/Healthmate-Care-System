package om.healthmate.javaproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Kiểm tra xem password đã được mã hóa chưa (BCrypt hash bắt đầu bằng $2a$, $2b$, hoặc $2y$)
     */
    private boolean isPasswordEncoded(String password) {
        return password != null && (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$"));
    }

    public User register(User user) {
        // Nếu chưa set role thì mặc định là USER
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        // Mã hóa password khi đăng ký
        if (user.getPassword() != null && !isPasswordEncoded(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || user.getPassword() == null) {
            return null;
        }
        
        // Kiểm tra password: nếu đã mã hóa thì dùng passwordEncoder, nếu chưa thì so sánh plain text (tương thích với dữ liệu cũ)
        boolean passwordMatches;
        if (isPasswordEncoded(user.getPassword())) {
            // Password đã được mã hóa, dùng passwordEncoder để so sánh
            passwordMatches = passwordEncoder.matches(password, user.getPassword());
            // Nếu đăng nhập thành công và password chưa được mã hóa trong DB, cập nhật lại
            if (passwordMatches) {
                // Password đã được mã hóa rồi, không cần làm gì
            }
        } else {
            // Password chưa được mã hóa (dữ liệu cũ), so sánh plain text
            passwordMatches = user.getPassword().equals(password);
            // Nếu đăng nhập thành công, mã hóa password và lưu lại
            if (passwordMatches) {
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
            }
        }
        
        if (passwordMatches) {
            return user;
        }
        return null;
    }

    public void save(User user) {
        // Nếu password được set và chưa mã hóa, mã hóa nó
        if (user.getPassword() != null && !isPasswordEncoded(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public java.util.List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
} 