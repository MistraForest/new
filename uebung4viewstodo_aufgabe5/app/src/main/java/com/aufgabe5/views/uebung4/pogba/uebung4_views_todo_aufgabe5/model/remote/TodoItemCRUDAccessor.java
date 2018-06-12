package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.remote;

import com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model.Todo_Simple;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/dataitems")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface TodoItemCRUDAccessor {
	
	@GET
	public List<Todo_Simple> readAllItems();

	@GET
	@Path("/{itemId}")
	Todo_Simple readItem(@PathParam("itemId") long id);

	@POST
	public Todo_Simple createItem(Todo_Simple item);

	@DELETE
	@Path("/{itemId}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public boolean deleteItem(@PathParam("itemId") long itemId);

	@PUT
	public Todo_Simple updateItem(Todo_Simple item);


}
