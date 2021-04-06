package ru.af3412.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HbmTrackerTest {

    private HbmTracker hbmTracker;

    @Before
    public void initHbmTracker() {
        this.hbmTracker = new HbmTracker();
    }

    @After
    public void closeHbmTracker() {
        this.hbmTracker.close();
    }

    @Test
    public void whenAddItemThenThisItemExistsInAllListItem() {
        Item item = new Item("Item 1", "desc 1", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(item);

        List<Item> result = hbmTracker.findAll();

        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Item 1"));
        assertThat(result.get(0).getDescription(), is("desc 1"));

    }

    @Test
    public void whenDeleteItemThenSizeResultListIsZero() {
        Item item = new Item("Item 1", "desc 1", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(item);

        List<Item> result = hbmTracker.findAll();
        hbmTracker.delete(result.get(0).getId());
        List<Item> expected = hbmTracker.findAll();
        assertThat(expected.size(), is(0));

    }

    @Test
    public void whenFindItemByNameThenReturnThisItem() {
        Item item = new Item("Item 1", "desc 1", new Timestamp(System.currentTimeMillis()));
        Item item2 = new Item("Item 1", "desc 2", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(item);
        hbmTracker.add(item2);

        var result = hbmTracker.findByName("Item 1");
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDescription(), is("desc 1"));
        assertThat(result.get(1).getDescription(), is("desc 2"));

    }

}