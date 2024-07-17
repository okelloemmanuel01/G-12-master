<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Representative;

class RepresentativeController extends Controller
{
    public function store(Request $request)
    {
        // Validate the input data
        $request->validate([
            'repName' => 'required',
            'repEmail' => 'required|email',
            'schoolRegNo' => 'required',
        ]);

        // Create a new Representative instance and save it to the database
        Representative::create([
            'repName' => $request->input('repName'),
            'repEmail' => $request->input('repEmail'),
            'schoolRegNo' => $request->input('schoolRegNo'),
        ]);

        // Redirect back or show a success message
        return redirect()->back()->with('success', 'Representative added successfully!');
    }
}
