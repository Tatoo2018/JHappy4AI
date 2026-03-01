package com.jhappy.jhappy4ai.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.jupiter.api.Test;

public class HandlerTest {

    @Test
    public void testWorkspaceExists() {
        // Eclipseのワークスペースが取得できるかテスト
        assertNotNull(ResourcesPlugin.getWorkspace().getRoot());
    }
}