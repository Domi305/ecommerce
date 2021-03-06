import React from 'react'
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

export default function Basket(props) {
    return (
        <Paper>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Produkt</TableCell>
                            <TableCell>Cena</TableCell>
                            <TableCell>Ilość</TableCell>
                            <TableCell>Koszt</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {Object.values(props.items || {}).length <= 0 ? <p>Najpierw dodaj cos do koszyka</p> : null}
                        {Object.values(props.items || {}).map(item => <BasketItem id={item.id} name={item.title}
                                                                                  price={item.price}
                                                                                  count={item.count}/>)}
                    </TableBody>
                </Table>
            </TableContainer>
            {props.children}
        </Paper>
    );
}

function BasketItem(props) {
    return (
        <TableRow key={props.id}>
            <TableCell component="th" scope="row">{props.name}</TableCell>
            <TableCell>{props.price}</TableCell>
            <TableCell>{props.count}</TableCell>
            <TableCell>{props.price * props.count}</TableCell>
        </TableRow>
    );
}