package ru.af3412.tracker;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HbmTrackerTest {

    @Test
    public void whenAddItemThenThisItemExistsInAllListItem() {
        try (HbmTracker hbmTracker = new HbmTracker()) {
            Item item = new Item("Item 1", "desc 1", new Timestamp(System.currentTimeMillis()));
            hbmTracker.add(item);

            List<Item> result = hbmTracker.findAll();

            assertThat(result.size(), is(1));
            assertThat(result.get(0).getName(), is("Item 1"));
            assertThat(result.get(0).getDescription(), is("desc 1"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteItemThenSizeResultListIsZero() {
        try (HbmTracker hbmTracker = new HbmTracker()) {
            Item item = new Item("Item 1", "desc 1", new Timestamp(System.currentTimeMillis()));
            hbmTracker.add(item);

            List<Item> result = hbmTracker.findAll();
            hbmTracker.delete(result.get(0).getId());
            assertThat(result.size(), is(0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindItemByNameThenReturnThisItem() {
        try (HbmTracker hbmTracker = new HbmTracker()) {
            Item item = new Item("Item 1", "desc 1", new Timestamp(System.currentTimeMillis()));
            Item item2 = new Item("Item 1", "desc 2", new Timestamp(System.currentTimeMillis()));
            hbmTracker.add(item);
            hbmTracker.add(item2);

            var result = hbmTracker.findByName("Item 1");
            assertThat(result.size(), is(2));
            assertThat(result.get(0).getDescription(), is("desc 1"));
            assertThat(result.get(1).getDescription(), is("desc 2"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}