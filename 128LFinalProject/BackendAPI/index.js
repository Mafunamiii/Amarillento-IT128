const express = require('express');
const mongoose = require('mongoose');

const app = express();
const PORT = 8080;

mongoose.connect('mongodb://localhost:27017/newinventory01', {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(() => {
    console.log('Connected to MongoDB');
}).catch((err) => {
    console.error('Failed to connect to MongoDB', err);
});

const itemSchema = new mongoose.Schema({
    itemId: { type: Number, unique: true, index: true },
    name: String,
    quantity: Number,
    category: String
}, { versionKey: false });


const Item = mongoose.model('Item', itemSchema);

app.use(express.json());

app.listen(PORT, () => {
    console.log(`App listening on port http://localhost:${PORT}`);
});

app.get('/item', async (req, res) => {
    try {
        const items = await Item.find();
        res.status(200).json(items);
    } catch (error) {
        console.error(error);
        res.status(500).send('Internal server error');
    }
});

app.post('/item/additem', (req, res) => {
    const { itemId, name, quantity, category } = req.body;
    const newItem = new Item({ itemId, name, quantity, category });
    newItem.save()
        .then((item) => {
            res.status(200).send(`Item ${item.itemId} created`);
        })
        .catch((error) => {
            res.status(500).send(error);
        });
});


app.patch('/item/patchitem/:itemId', async (req, res) => {
    try {
        const { itemId } = req.params;
        const update = req.body;
        const item = await Item.findOneAndUpdate({ itemId }, update);
        res.status(200).json(item);
    } catch (error) {
        console.error(error);
        res.status(500).send('Internal server error');
    }
});

app.put('/item/updateitem/:itemId', async (req, res) => {
    try {
        const { itemId } = req.params;
        const update = req.body;
        delete update._id; // exclude the _id field from the update
        const item = await Item.findOneAndUpdate({ itemId }, update);
        res.status(200).json(item);
    } catch (error) {
        console.error(error);
        res.status(500).send('Internal server error');
    }
});


app.delete('/item/deleteitem/:itemId', async (req, res) => {
    try {
        const { itemId } = req.params;
        await Item.findOneAndDelete({ itemId: itemId });
        res.status(200).send(`Item ${itemId} deleted`);
    } catch (error) {
        console.error(error);
        res.status(500).send('Internal server error');
    }
});

