package ut.com.zuhlke.training.jira;

import org.junit.Test;
import com.zuhlke.training.jira.api.MyPluginComponent;
import com.zuhlke.training.jira.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}