import {createSlice, createAsyncThunk} from '@reduxjs/toolkit';
import api from "../../api";

export const getAllProductCategories = createAsyncThunk("GET_ALL_PRODUCT_CATEGORIES", async (_, thunkAPI) => {
    //if (thunkAPI.getState().product_categories.length == 0) {
        let response = await api.get("http://localhost:8080/products/categories");
        return response.data
/*    } else {
        return thunkAPI.getState().product_categories;
    }*/
});

export const productCategorySlice = createSlice({
    name: 'product_categories',
    initialState: {tree: [], byId: {}},
    reducers: {
        // standard reducer logic, with auto-generated action types per reducer
    },
    extraReducers: {
        [getAllProductCategories.fulfilled]: (state, action) => {
            let forest = createTree(action.payload);
            let roots = forest[null].children;
            delete forest[null];

            state.tree = roots;
            state.byId = forest;
        }
    }
})

function createTree(treeItems) {
    let treeItemById = treeItems.reduce((map, item) => {
        map[item.id] = {children: [], ...item};
        return map;
    }, {});

    treeItemById[null] = {children: []};

    for (const item of treeItems) {
        let parentCategory = treeItemById[item.parentCategoryId];
        let childCategory = treeItemById[item.id];

        parentCategory.children.push(childCategory);
        childCategory.parentId = parentCategory.id
    }

    return treeItemById;
}