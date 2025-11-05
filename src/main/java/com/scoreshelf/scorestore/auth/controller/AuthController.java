package com.scoreshelf.scorestore.auth.controller;
import com.scoreshelf.scorestore.auth.service.AuthServiceImpl;
import com.scoreshelf.scorestore.base.entity.Cart;
import com.scoreshelf.scorestore.base.entity.MusicScore;
import com.scoreshelf.scorestore.base.entity.User;
import com.scoreshelf.scorestore.base.service.ICartService;
import com.scoreshelf.scorestore.base.service.IMusicScoreService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private IMusicScoreService musicScoreService;

    @Autowired
    private ICartService cartService;

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; 
    }

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String username, @RequestParam String email,
            @RequestParam String password, HttpSession session) {
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole("customer"); 
        newUser.setOwnedScoreIds(null); 

        authService.registerUser(newUser);
        
        session.setAttribute("username", newUser.getUsername());
        session.setAttribute("role", newUser.getRole());

        return "redirect:/customer-homepage"; 
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = authService.validateUserAndGetUser(username, password);
        if (user != null) {
            
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            if ("customer".equals(user.getRole())) {
                return "redirect:/customer-homepage"; 
            } else if ("admin".equals(user.getRole())) {
                return "redirect:/admin-dashboard"; 
            }
        }
        return "redirect:/login?error"; 
    }

    @GetMapping("/customer-homepage")
    public String customerHomepage(Model model, HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"customer".equals(role)) {
            return "redirect:/login"; 
        }

        User currentUser = authService.getCurrentUser(username);
        List<String> ownedScoreIds = currentUser.getOwnedScoreIds();
        
        List<MusicScore> scoresNotOwned = musicScoreService.getMusicScoresNotOwnedByUser(ownedScoreIds);
        model.addAttribute("scoresNotOwned", scoresNotOwned);
        
        Cart cart = cartService.getCartByUserId(currentUser.getId());
        List<String> cartScoreIds = cart != null ? cart.getScoreIdsList() : List.of();
        model.addAttribute("cartScoreIds", cartScoreIds);

        return "customer/customer-homepage"; 
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"admin".equals(role)) {
            return "redirect:/login"; 
        }
 
        List<MusicScore> allMusicScores = musicScoreService.getAllMusicScores();
        model.addAttribute("musicScores", allMusicScores);
      
        List<User> allUsers = authService.getAllUsers();
        model.addAttribute("users", allUsers);

        return "admin/admin-dashboard"; 
    }

    @GetMapping("/customer-user-profile")
    public String customerUserProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(username);
        model.addAttribute("user", currentUser);
        return "customer/customer-user-profile";
    }

    @GetMapping("/admin-user-profile")
    public String adminUserProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(username);
        model.addAttribute("user", currentUser);
        return "admin/admin-user-profile";
    }

    @PostMapping("/customer-update-profile")
    public String customerUpdateProfile(@RequestParam String username, @RequestParam String email,
            @RequestParam String password, HttpSession session) {
        String sessionUsername = (String) session.getAttribute("username");
        if (sessionUsername == null) {
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(sessionUsername);
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        authService.updateUser(currentUser);

        session.setAttribute("username", username); 
        return "redirect:/customer-homepage";
    }

    @PostMapping("/admin-update-profile")
    public String adminUpdateProfile(@RequestParam String username, @RequestParam String email,
            @RequestParam String password, HttpSession session) {
        String sessionUsername = (String) session.getAttribute("username");
        if (sessionUsername == null) {
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(sessionUsername);
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        authService.updateUser(currentUser);

        session.setAttribute("username", username); 
        return "redirect:/admin-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login"; 
    }
}