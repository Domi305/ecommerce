import {
    Grid,
    Card,
    CardActionArea,
    CardContent,
    CardMedia,
    CardActions,
    Typography,
} from "@material-ui/core";
import React from "react";
import Price from "./Price";
import {Link as RouterLink} from "react-router-dom";
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';
import IconButton from "@material-ui/core/IconButton";
import MoreIcon from '@material-ui/icons/More';


export default function ProductsGrid(props) {

    return (
        <Grid container direction="row" justify="flex-start" alignItems="flex-start" spacing={1}>
            {props.products.map(product => <Grid item>{ProductGridItem({
                ...product,
                addToBasket: props.addToBasket
            })}</Grid>)}
        </Grid>
    );
}

function ProductGridItem(props) {
    return (
        <Card key={props.id} style={{width: "300px", height: "300px"}}>
            <CardActionArea style={{position: "relative"}}>
                <CardMedia image={props.thumbnailUrl} title={props.name} component="img"
                           style={{maxWidth: "100%", maxHeight: "175px"}}/>
                <Price value={props.price} style={{
                    display: "block",
                    position: "relative",
                    top: "-20pt",
                    float: "right",
                    backgroundColor: " black",
                    color: "white",
                    margin: "1pt",
                    padding: "1pt"
                }}/>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        {props.name}
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                        {props.description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
                <RouterLink to={"/products/" + props.id}>
                    <IconButton size="small"><MoreIcon/> </IconButton>
                </RouterLink>
                <IconButton size="small"><ShoppingBasketIcon onClick={() => props.addToBasket(props.id)}/></IconButton>
            </CardActions>
        </Card>
    );
}
