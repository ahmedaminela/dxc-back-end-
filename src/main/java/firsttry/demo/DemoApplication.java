package firsttry.demo;

import firsttry.demo.DTO.PermissionVo;
import firsttry.demo.DTO.RoleVo;
import firsttry.demo.DTO.UserVo;
import firsttry.demo.Service.IUserService;
import firsttry.demo.enums.Permissions;
import firsttry.demo.enums.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner initDataBase(IUserService userService) {
		return args -> {
			Arrays.stream(Permissions.values()).toList().forEach(permissions ->
					userService.save(PermissionVo.builder().authority(permissions.name()).build()));

			RoleVo Admin = RoleVo.builder()
					.authority(Roles.Admin.name()).
			authorities(List.of(
					userService.getPermissionByName(Permissions.Creer_user.name()),
					userService.getPermissionByName(Permissions.Modifier_user.name()),
					userService.getPermissionByName(Permissions.Supprimer_user.name()),
					userService.getPermissionByName(Permissions.Get_all_users.name()),
					userService.getPermissionByName(Permissions.Envoyer_email.name()),
					userService.getPermissionByName(Permissions.Get_User_byid.name())
					)).
					build();
			RoleVo Manager = RoleVo.builder()
					.authority(Roles.Manager.name()).
					authorities(List.of(
							userService.getPermissionByName(Permissions.Creer_projet.name()),
							userService.getPermissionByName(Permissions.Modifier_projet.name()),
							userService.getPermissionByName(Permissions.Supprimer_projet.name()),
							userService.getPermissionByName(Permissions.Get_all_conge.name()),
							userService.getPermissionByName(Permissions.Decision_conge.name()),
							userService.getPermissionByName(Permissions.Export_details_projet.name()),
							userService.getPermissionByName(Permissions.Creer_equipe.name()),
							userService.getPermissionByName(Permissions.Ajouter_membre.name()),
							userService.getPermissionByName(Permissions.Supprimer_membre.name()),
							userService.getPermissionByName(Permissions.Envoyer_mssg.name())
							)).
					build();

			RoleVo Chefprojet = RoleVo.builder()
					.authority(Roles.Chef_projet.name())
					.authorities(List.of(
							userService.getPermissionByName(Permissions.Gerer_lesbacklogs.name()),
							userService.getPermissionByName(Permissions.Creer_tache.name()),
							userService.getPermissionByName(Permissions.Modifier_tache.name()),
							userService.getPermissionByName(Permissions.Supprimer_tache.name()),
							userService.getPermissionByName(Permissions.Assigner_tache_membre.name()),
							userService.getPermissionByName(Permissions.Prioriser_tache.name()),
							userService.getPermissionByName(Permissions.Gerer_sprint.name()),
							userService.getPermissionByName(Permissions.Modifier_sprint.name()),
							userService.getPermissionByName(Permissions.Supprimer_sprint.name()),
							userService.getPermissionByName(Permissions.Planifier_obj_sprint.name()),
							userService.getPermissionByName(Permissions.Assigner_tache_sprint.name()),
							userService.getPermissionByName(Permissions.Demande_conge.name())

							)).build();

			RoleVo Membre = RoleVo.builder()
					.authority(Roles.membre.name()).
			authorities(List.of(

					userService.getPermissionByName(Permissions.Demande_conge.name()),
					userService.getPermissionByName(Permissions.Get_all_tache.name()),
					userService.getPermissionByName(Permissions.Envoyer_mssg.name()),
					userService.getPermissionByName(Permissions.Modifier_status_tache.name()))).
					build();

			userService.save(Admin);
			userService.save(Chefprojet);
			userService.save(Manager);
			userService.save(Membre);



			UserVo admin = UserVo.builder()
					.username("admin")
					.password("admin")
					.authorities(List.of(Admin)	)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.build();

			UserVo manager = UserVo.builder()
					.username("mana")
					.password("mana")
					.authorities(List.of(Manager))
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.build();

			UserVo chef_projet = UserVo.builder()
					.username("chefprojet")
					.password("chefprojet")
					.authorities(List.of(Chefprojet))
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.build();
			UserVo membres = UserVo.builder()
					.username("membre")
					.password("membre")
					.authorities(List.of(Membre))
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.build();



			userService.save(admin);
			userService.save(manager);
			userService.save(chef_projet);
			userService.save(membres);

		};
	}


}
