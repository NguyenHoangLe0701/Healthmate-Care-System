package om.healthmate.javaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.service.UserService;

@Controller
@RequestMapping("/dashboard/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("content", "dashboard/user-management :: contentUser");
        return "dashboard/user-layout";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/dashboard/users";
        }
        model.addAttribute("user", user);
        return "dashboard/users/edit";
    }


    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam(required = false) String role) {
        // Nếu không chọn role thì mặc định là USER
        if (role != null && !role.isEmpty()) {
            user.setRole(role);
        } else if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        userService.save(user);
        return "redirect:/dashboard/users";
    }


    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user, @RequestParam(required = false) String role) {
        if (role != null && !role.isEmpty()) {
            user.setRole(role);
        } else if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        userService.save(user);
        return "redirect:/dashboard/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/dashboard/users";
    }
} 