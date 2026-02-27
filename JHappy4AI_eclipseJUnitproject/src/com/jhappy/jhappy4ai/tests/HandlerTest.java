package com.jhappy.jhappy4ai.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.jupiter.api.Test;

class HandlerTest {

    @Test
    void testWorkspaceExists() {
        // Eclipseのワークスペースが取得できるかテスト
        assertNotNull(ResourcesPlugin.getWorkspace().getRoot());
    }
}