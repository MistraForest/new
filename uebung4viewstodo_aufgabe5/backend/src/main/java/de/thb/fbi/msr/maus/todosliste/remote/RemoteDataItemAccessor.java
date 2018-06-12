package de.thb.fbi.msr.maus.todosliste.remote;

import de.thb.fbi.msr.maus.todosliste.model.TodoItemCRUDAccessor;
import de.thb.fbi.msr.maus.todosliste.model.Todo_Simple;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RemoteDataItemAccessor implements TodoItemCRUDAccessor {

	protected static Logger logger = Logger
			.getLogger(RemoteDataItemAccessor.class);

	/**
	 * the list of data items, note that the list is *static* as for each client
	 * request a new instance of this class will be created!
	 */
	private static List<Todo_Simple> itemlist = new ArrayList<>();

	/**
	 * we assign the ids here
	 */
	private static long idCount = 0;
	
	@Override
	public List<Todo_Simple> readAllItems() {
		logger.info("readAllItems(): " + itemlist);

		return itemlist;
	}

	@Override
	public Todo_Simple readItem(long id) {
		logger.info("readItem(): " + id);
		return itemlist.get((int)id);
	}

	@Override
	public Todo_Simple createItem(Todo_Simple item) {
		logger.info("createItem(): " + item);
		item.setId(idCount++);

		itemlist.add(item);
		return item;
	}

	@Override
	public boolean deleteItem(final long itemId) {
		logger.info("deleteItem(): " + itemId);

		boolean removed = itemlist.remove(new Todo_Simple() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 71193783355593985L;

			@Override
			public long getId() {
				return itemId;
			}
		});

		return removed;
	}

	@Override
	public Todo_Simple updateItem(Todo_Simple item) {
		logger.info("updateItem(): " + item);

		return itemlist.get(itemlist.indexOf(item)).updateFrom(item);
	}
}
