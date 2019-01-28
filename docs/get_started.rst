.. _Get Started:

Get Started
===========
	
 1. Start Eclipse
 2. Create Maven Projects
 3. Import Graphenee Dependecies
 4. Update Maven Project
 5. Run as Debug Mode

   	
   .. code-block:: bash
	
	package com.myapp.todoapp;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;

 	@SpringBootApplication
	public class Application {

		public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
		}

	}	

 6. Java main class look like this


  .. code-block:: bash

	package com.myapp.todoapp;

	import org.springframework.boot.builder.SpringApplicationBuilder;
	import org.springframework.boot.web.support.SpringBootServletInitializer;

	public class ServletInitializer extends SpringBootServletInitializer {

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
			// configure application for web container
			return builder.sources(Application.class);
		}

	}	

 7. Create a new class with name servletinitializer and extends it with spring servlet initializer , override a configure method here in this class and it will return the spring application builder to main class “application.java”
 8. Create another java class, extend it with abstract dashboard setup and add unimplemented methods, annotate it with Service and uiscope, view of application first page.
 9. Create mainui.java extend it with adstractdashboardui, add unimplemented method and autowire spring view and applicationdashboard here. Annotate it with springUI, theme, push, viewport

  .. code-block:: bash 
 	
	package com.myapp.todoapp;

	import org.springframework.beans.factory.annotation.Autowired;
	import com.vaadin.annotations.Push;
	import com.vaadin.annotations.Theme;
	import com.vaadin.annotations.Viewport;
	import com.vaadin.shared.communication.PushMode;
	import com.vaadin.shared.ui.ui.Transport;
	import com.vaadin.spring.annotation.SpringUI;
	import com.vaadin.spring.navigator.SpringViewProvider;
	import io.graphenee.vaadin.AbstractDashboardSetup;
	import io.graphenee.vaadin.AbstractDashboardUI;
	import com.myapp.todoapp.DashboardSetup;

	@SpringUI
	@Theme("graphenee")
	@Push(transport = Transport.WEBSOCKET, value = PushMode.MANUAL)
	@Viewport(value = "width = device-width")
	public class MainUI extends AbstractDashboardUI {

	@Autowired
	SpringViewProvider viewProvider;

	@Autowired
	DashboardSetup dashboardSetup;

	@Override
	protected AbstractDashboardSetup dashboardSetup() {
		// return a view
		return dashboardSetup;
	}

	}
 
 10. Create another class, extend it with Abstract dashboard panel and implements mview, must annotate it with springview , add this in dashboardsetup.java
 11. Dashboardviewname() return default view.java in DashboardSetup.java

  .. code-block:: bash
	
		@Override
		public String dashboardViewName() {
			// Add default view name here
		return DefaultDashboardView.VIEW_NAME;
		}

 12. Create a database connection, graphenee need a database and save it with the name as “application.properties” inside the resources packages. 

  .. code-block:: bash

	flyway.enabled=false
	spring.datasource.url=jdbc:h2:mem:tododb  //databasename
	spring.datasource.username=demo 
	spring.datasource.password=password
	spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

 


	
