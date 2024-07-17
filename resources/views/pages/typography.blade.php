@extends('layouts.app', ['activePage' => 'typography', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Typography', 'activeButton' => 'laravel'])

@section('content')
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Light Bootstrap Table Heading</h4>
                            <p class="card-category">Created using Montserrat Font Family</p>
                        </div>
                    </div>   
                    <form method="post" action="{{ route('representatives.store') }}">
    @csrf <!-- Add the CSRF token -->
    <input type="text" name="repName" placeholder="Representative Name">
    <input type="email" name="repEmail" placeholder="Representative Email">
    <input type="text" name="schoolRegNo" placeholder="School Registration Number">
    <!-- Other form fields if needed -->
    <button type="submit">Submit</button>
</form>
                </div>
            </div>
        </div>
    </div>
@endsection