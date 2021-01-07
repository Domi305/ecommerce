import React from 'react';
import {Paper} from "@material-ui/core";
import ProductCategoryTree from "../product_categories/ProductCategoryTree";
export default function ProductsPage(props) {
    return (
        <Paper style={{padding: "1em"}}>
            <ProductCategoryTree/>
            </Paper>
    );
}