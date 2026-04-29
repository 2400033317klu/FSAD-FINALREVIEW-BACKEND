package com.career.platform;

import com.career.platform.entity.Appointment;
import com.career.platform.entity.Career;
import com.career.platform.entity.CareerResource;
import com.career.platform.entity.Session;
import com.career.platform.entity.User;
import com.career.platform.repository.AppointmentRepository;
import com.career.platform.repository.CareerRepository;
import com.career.platform.repository.CareerResourceRepository;
import com.career.platform.repository.SessionRepository;
import com.career.platform.repository.UserRepository;
import com.career.platform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PlatformApplication {

    private static final Logger logger = LoggerFactory.getLogger(PlatformApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            CareerResourceRepository resourceRepo,
            AppointmentRepository appointmentRepo,
            CareerRepository careerRepo,
            SessionRepository sessionRepo,
            UserRepository userRepo,
            UserService userService,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // ── 1. Career Resources ───────────────────────────────────────────
            if (resourceRepo.count() == 0) {
                resourceRepo.save(CareerResource.builder().title("Full Stack Developer").category("Tech")
                        .description("Master front-end and back-end development with React and Spring Boot.")
                        .rating(4.8).link("https://roadmap.sh/full-stack").build());
                resourceRepo.save(CareerResource.builder().title("UX/UI Designer").category("Design")
                        .description("Learn the art of creating user-centric digital experiences.")
                        .rating(4.9).link("https://roadmap.sh/ux-design").build());
                resourceRepo.save(CareerResource.builder().title("Data Scientist").category("Tech")
                        .description("Harness the power of data to derive meaningful insights.")
                        .rating(4.7).link("https://roadmap.sh/data-scientist").build());
                resourceRepo.save(CareerResource.builder().title("Marketing Strategist").category("Business")
                        .description("Develop campaigns that captivate and convert audiences.")
                        .rating(4.6).link("https://roadmap.sh/content-marketing").build());
                resourceRepo.save(CareerResource.builder().title("Cloud Architect").category("Tech")
                        .description("Design and manage scalable cloud infrastructure.")
                        .rating(4.8).link("https://roadmap.sh/cloud-architect").build());
                resourceRepo.save(CareerResource.builder().title("Android Developer").category("Tech")
                        .description("Build high-performance apps for Android using Kotlin.")
                        .rating(4.7).link("https://roadmap.sh/android").build());
                resourceRepo.save(CareerResource.builder().title("Cyber Security Analyst").category("Tech")
                        .description("Protect systems and networks from digital attacks and data breaches.")
                        .rating(4.9).link("https://roadmap.sh/cyber-security").build());
                resourceRepo.save(CareerResource.builder().title("Product Manager").category("Business")
                        .description("Lead the development and launch of successful products.")
                        .rating(4.5).link("https://roadmap.sh/product-management").build());
                resourceRepo.save(CareerResource.builder().title("DevOps Engineer").category("Tech")
                        .description("Bridge the gap between development and operations through automation.")
                        .rating(4.8).link("https://roadmap.sh/devops").build());
                resourceRepo.save(CareerResource.builder().title("Java Spring Boot Course").category("Learning")
                        .description("Comprehensive guide to building enterprise applications with Spring Boot.")
                        .rating(4.9).link("https://spring.io/projects/spring-boot").build());
                resourceRepo.save(CareerResource.builder().title("Python for Data Science").category("Learning")
                        .description("Master Python for data analysis, visualization, and machine learning.")
                        .rating(4.8).link("https://www.coursera.org/specializations/python-3-programming").build());
                resourceRepo.save(CareerResource.builder().title("Figma UI/UX Mastery").category("Materials")
                        .description("Design stunning user interfaces and prototypes using Figma.")
                        .rating(4.9).link("https://www.figma.com/resources/").build());
                resourceRepo.save(CareerResource.builder().title("AWS Cloud Essentials").category("Learning")
                        .description("Foundational knowledge of Amazon Web Services and cloud computing.")
                        .rating(4.7).link("https://aws.amazon.com/training/essentials/").build());
            }

            // ── 2. Career entries ─────────────────────────────────────────────
            if (careerRepo.count() == 0) {
                careerRepo.save(Career.builder().title("Full Stack Developer")
                        .description("Build end-to-end web applications using modern frameworks.")
                        .skillsRequired("Java, Spring Boot, React, SQL").category("Tech").build());
                careerRepo.save(Career.builder().title("Data Scientist")
                        .description("Analyse large datasets and build predictive models.")
                        .skillsRequired("Python, Machine Learning, SQL, Statistics").category("Tech").build());
                careerRepo.save(Career.builder().title("UX/UI Designer")
                        .description("Design intuitive and accessible user interfaces.")
                        .skillsRequired("Figma, User Research, Prototyping, CSS").category("Design").build());
                careerRepo.save(Career.builder().title("DevOps Engineer")
                        .description("Automate infrastructure and streamline CI/CD pipelines.")
                        .skillsRequired("Docker, Kubernetes, Jenkins, Linux, AWS").category("Tech").build());
                careerRepo.save(Career.builder().title("Cyber Security Analyst")
                        .description("Protect systems and networks from digital threats.")
                        .skillsRequired("Network Security, Ethical Hacking, SIEM, Python").category("Tech").build());
            }

            // ── 3. Demo users → appointments → sessions ───────────────────────
            // Only seed when the users table is completely empty (first run).
            if (userRepo.count() == 0) {
                User student = userRepo.save(User.builder()
                        .username("demo_student").email("student@demo.com")
                        .password(passwordEncoder.encode("password")).role("STUDENT").build());

                User counsellor = userRepo.save(User.builder()
                        .username("demo_counsellor").email("counsellor@demo.com")
                        .password(passwordEncoder.encode("password")).role("COUNSELLOR").build());

                userRepo.save(User.builder()
                        .username("admin").email("admin@demo.com")
                        .password(passwordEncoder.encode("admin123")).role("ADMIN").build());

                // Appointments — use the real saved student id
                appointmentRepo.save(Appointment.builder()
                        .studentId(student.getId()).counselorName("Dr. Sarah Wilson")
                        .topic("Career Strategy Review").date("2026-04-15")
                        .time("10:00 AM").status("SCHEDULED").build());
                appointmentRepo.save(Appointment.builder()
                        .studentId(student.getId()).counselorName("Mr. James Chen")
                        .topic("Mock Interview - Tech").date("2026-04-18")
                        .time("02:30 PM").status("SCHEDULED").build());

                // Sessions — linked to both student and counsellor
                sessionRepo.save(Session.builder()
                        .studentId(student.getId()).counsellorId(counsellor.getId())
                        .date("2026-05-10").status("SCHEDULED").build());
                sessionRepo.save(Session.builder()
                        .studentId(student.getId()).counsellorId(counsellor.getId())
                        .date("2026-05-17").status("SCHEDULED").build());
            }

            int normalizedCount = userService.normalizeCounsellorRoles();
            if (normalizedCount > 0) {
                logger.info("Normalized {} existing counsellor role(s) to exact COUNSELLOR", normalizedCount);
            }
        };
    }
}
