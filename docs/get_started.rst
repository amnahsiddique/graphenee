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

 13. Login/Authentication Page
 
 .. image:: images/loginPage.png
	:width: 800
	:alt: Alternative text

 14.First page After Login

 .. image:: images/firstView.png
	:width: 800
	:alt: Alternative text   
 
 14. Create a new java class, extend it with abstract entity list panel , create constructor, add unimplemented methods and annotate it with spring component and scope.

 .. code-block:: bash
	
	package com.myapp.todoapp;

	import java.util.ArrayList;
	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Scope;
	import com.vaadin.spring.annotation.SpringComponent;
	import io.graphenee.vaadin.AbstractEntityListPanel;
	import io.graphenee.vaadin.TRAbstractForm;

	@SpringComponent
	@Scope("prototype")
	public class TodoPanel extends AbstractEntityListPanel<TodoListBean> {

	@Autowired
	TodoForm todoForm;

	List<TodoListBean> list = new ArrayList<>();

	public TodoPanel() {
		super(TodoListBean.class);
	}

	@Override
	protected boolean onSaveEntity(TodoListBean entity) {
		//on save button action
		return false;
	}

	@Override
	protected boolean onDeleteEntity(TodoListBean entity) {
		// on delete button action
		return false;
	}

	@Override
	protected String panelCaption() {
		// Provide Panel Name here in string
		return null;
	}

	@Override
	protected List<TodoListBean> fetchEntities() {
		return list;
	}

	@Override
	protected String[] visibleProperties() {
		//provide the name you want to show in panel from you data
		return null;
	}

	@Override
	protected TRAbstractForm<TodoListBean> editorForm() {
		//return form here
		return null;
	}
	}

 15. Autowire panel class here in DefaultDashboardView.java, refresh this paneland add pannel componnet in view 

 .. code-block:: bash
	 
	package com.myapp.todoapp;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Scope;
	import org.vaadin.viritin.navigator.MView;
	import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
	import com.vaadin.spring.annotation.SpringView;
	import io.graphenee.vaadin.AbstractDashboardPanel;

	@SpringView(name = DefaultDashboardView.VIEW_NAME)
	@Scope("prototype")
	public class DefaultDashboardView extends AbstractDashboardPanel implements MView {

	public static final String VIEW_NAME = "todo-list";

	@Autowired
	TodoPanel todoPanel;

	@Override
	public void enter(ViewChangeEvent event) {
		//always call before view build
		todoPanel.refresh();
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
	}

	@Override
	protected String panelTitle() {
		// Panel Name Optional
		return "ToDo Panel";
	}

	@Override
	protected void postInitialize() {
		// build component 
		addComponent(todoPanel.build());
	}

	@Override
	protected boolean shouldShowHeader() {
		return true;
	}

	}

 16. View with panel, Panel have default Buttons 

 .. image:: images/panel.png
	:width: 800
	:alt: alternate text

 17. Create a new java class (bean), which have getter setter

 .. code-block: bash

 	package com.myapp.todoapp;

	public class TodoListBean {

	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	}
	
 18. Create a new class, for form, extend it with trAbstractForm and provide type of Bean which is created.

 .. code-block: bash

	package com.myapp.todoapp;

	import org.springframework.context.annotation.Scope;
	import org.vaadin.viritin.fields.MTextField;
	import com.vaadin.data.fieldgroup.PropertyId;
	import com.vaadin.spring.annotation.SpringComponent;
	import com.vaadin.ui.FormLayout;
	import io.graphenee.vaadin.TRAbstractForm;

	@SuppressWarnings("serial")
	@SpringComponent
	@Scope("prototype")
	public class TodoForm extends TRAbstractForm<TodoListBean> {

	@PropertyId("title")
	MTextField titleTextField;

	@PropertyId("description")
	MTextField descriptionTextField;

	@Override
	protected void addFieldsToForm(FormLayout form) {
		titleTextField = new MTextField("Title").withRequired(true);
		descriptionTextField = new MTextField("Description").withRequired(true);
		form.addComponents(titleTextField, descriptionTextField);
	}

	@Override
	protected boolean eagerValidationEnabled() {
		return false;
	}

	@Override
	protected String formTitle() {
		return "Todo List";
	}

	@Override
	protected String popupWidth() {
		return "400px";
	}

	@Override
	protected String popupHeight() {
		return "200px";
	}

	}	

 19. Form 

 .. image:: images/form.png
	:width: 800
	:alt: alternate text

 20. Add CRUD operations in TodoPanel.java

 .. code-blocl: bash

	package com.myapp.todoapp;

	import java.util.ArrayList;
	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Scope;
	import com.vaadin.spring.annotation.SpringComponent;
	import io.graphenee.vaadin.AbstractEntityListPanel;
	import io.graphenee.vaadin.TRAbstractForm;

	@SpringComponent
	@Scope("prototype")
	public class TodoPanel extends AbstractEntityListPanel<TodoListBean> {

	@Autowired
	TodoForm todoForm;

	List<TodoListBean> list = new ArrayList<>();

	public TodoPanel() {
		super(TodoListBean.class);
	}

	@Override
	protected boolean onSaveEntity(TodoListBean entity) {
		//on save button action
		TodoListBean bean = new TodoListBean();
		bean.setTitle(entity.getTitle());
		bean.setDescription(entity.getDescription());
		list.add(bean);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(TodoListBean entity) {
		// on delete button action
		list.remove(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		// Provide Panel Name here in string
		return null;
	}

	@Override
	protected List<TodoListBean> fetchEntities() {
		return list;
	}

	@Override
	protected String[] visibleProperties() {
		//provide the name you want to show in panel from you data
		return new String[] { "title", "description" };
	}

	@Override
	protected TRAbstractForm<TodoListBean> editorForm() {
		//return form here
		return todoForm;
	}
	}

 19. Our TOdo application

 .. image:: images/app.png
	:width: 800 
	:alt: alternate 
