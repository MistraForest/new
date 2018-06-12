package com.aufgabe5.views.uebung4.pogba.uebung4_views_todo_aufgabe5.model;

/*@Path("/dataitems")
@Consumes({ "application/json" })
@Produces({ "application/json" })*/
public interface TodoDetailAccessor {

   /* @GET
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
    public Todo_Simple updateItem(Todo_Simple item);*/

    public Todo_Simple readItem();

    public void writeItem();

    public boolean hasItem();

    public void createItem();

    public void deleteItem();
}
