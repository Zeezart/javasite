package tech.justjava.testhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.justjava.testhome.dto.TestimonialDto;
import tech.justjava.testhome.service.TestimonialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class homeController {

    private final TestimonialService testimonialService;

    private String heroText =
            "We help start-ups and scale-ups build, launch, and maintain reliable software with dedicated engineering pods and a structured delivery process.";

    public homeController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    // ================= PUBLIC & ADMIN PAGES =================

    @GetMapping("/public")
    public String publicPage(Model model) {
        model.addAttribute("testimonials", testimonialService.getAllTestimonials());
        model.addAttribute("heroText", heroText);
        model.addAttribute("isAdminView", false);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("testimonials", testimonialService.getAllTestimonials());
        model.addAttribute("heroText", heroText);
        model.addAttribute("isAdminView", true);
        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/public";
    }

    // ================= STATIC PAGES =================

    @GetMapping("/about")
    public String aboutPage() {
        // Returns about.html from templates folder
        return "aboutpage";
    }

    @GetMapping("/designos")
    public String designos() {
        // Returns designos.html from templates folder
        return "designos";
    }

    @GetMapping("/services")
    public String services() {
        // Returns designos.html from templates folder
        return "services";
    }

    // THIS WAS THE MISSING PIECE CAUSING THE 404 ERROR
    @GetMapping("/insights")
    public String insightsPage() {
        // Returns insights.html from templates folder
        return "insights";
    }

    // ================= HERO TEXT (ADMIN) =================

    private static final Logger log =
            LoggerFactory.getLogger(homeController.class);

    @PostMapping("/save-text")
    public String saveText(@RequestParam String heroText) {
        log.info("Hero text updated: {}", heroText);
        this.heroText = heroText;
        return "redirect:/admin";
    }

    // ================= TESTIMONIAL CRUD (DTO) =================

    // ADD
    @PostMapping("/admin/testimonials/add")
    public String addTestimonial(TestimonialDto dto) {
        testimonialService.addTestimonial(dto);
        return "redirect:/admin";
    }

    // EDIT
    @PostMapping("/admin/testimonials/edit/{id}")
    public String editTestimonial(@PathVariable Long id, TestimonialDto dto) {
        testimonialService.updateTestimonial(id, dto);
        return "redirect:/admin";
    }

    // DELETE
    @PostMapping("/admin/testimonials/delete/{id}")
    public String deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return "redirect:/admin";
    }
}